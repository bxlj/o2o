package com.lj.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.lj.o2o.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
  * 图片处理
 * @author 贾
 *
 */
public class ImageUtil {

	//private static String basePath = PathUtil.getImgBasePath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHss");
	private static final Random r = new Random();
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	/**
	 * 将CommonsMultipartFile转换为File类
	 * @param cFlie
	 * @return
	 */
	public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFlie) {
		File newFile = new File(cFlie.getOriginalFilename());
		try {
			cFlie.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}
	
	/**
	 * 处理缩略图，并返回新生成图片的相对值路径
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(ImageHolder thumbnail,String targetAddr) {
		//文件名称
		String realFileName = getRandomFileName();
		//获取随机文件流的扩展名
		String extension = getFileExtension(thumbnail.getImageName());
		//创建目标路径所涉及到的路径
		makeDirPath(targetAddr);
		//获取文件存储相对路径(带文件夹名)
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is" + relativeAddr);
		//获取文件要保存到的目标路径
		File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
		logger.debug("current complete addr is :"+PathUtil.getImgBasePath()+relativeAddr);
		try {
			Thumbnails.of(thumbnail.getImage()).size(200,200).watermark(Positions.BOTTOM_RIGHT, 
					ImageIO.read(new File("F:\\image\\image\\watermark.jpg")), 0.25f)
			.outputQuality(0.8f).toFile(dest);
		}catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;
	}
	
	/**
	  *  处理详情图，并返回新生成图片的相对值路径
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateNormalImg(ImageHolder thumbnail,String targetAddr) {
		//获取不重复的文件名称
		String realFileName = getRandomFileName();
		//获取随机文件流的扩展名
		String extension = getFileExtension(thumbnail.getImageName());
		//创建目标路径所涉及到的路径
		makeDirPath(targetAddr);
		//获取文件存储相对路径(带文件夹名)
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is" + relativeAddr);
		//获取文件要保存到的目标路径
		File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
		logger.debug("current complete addr is :"+PathUtil.getImgBasePath()+relativeAddr);
		//调用Thumbnails生成带水印的图片
		try {
			Thumbnails.of(thumbnail.getImage()).size(337,640).watermark(Positions.BOTTOM_RIGHT, 
					ImageIO.read(new File("F:\\image\\image\\watermark.jpg")), 0.25f)
			.outputQuality(0.9f).toFile(dest);
		}catch (IOException e) {
			// TODO: handle exception
			logger.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;
	}
	
	/**
	  * 创建目标路径所涉及到的路径
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if(!dirPath.exists()) {
			//创建此抽象路径名指定的目录,包括所有必需但不存在的父目录。
			dirPath.mkdirs();
		}
	}

	/**
	 * 获取随机文件流的扩展名
	 * @param fileName
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	
	/**
	 * 生成随机文件名，当前年月日时钟分钟秒钟+五位随机数
	 * @return
	 */
	public static String getRandomFileName() {
		// 获取随机的五位数
		int rannum = r.nextInt(89999)+10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}
	
	/**
	 *   删除图片
	 *   storePath是文件的路径还是目录的路径
	 *   如果storePath 是文件的路径则删除该文件,
	 *   如果storePath 是目录的路径则删除该路径下所有的文件
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
		if(fileOrPath.exists()) {
			if(fileOrPath.isDirectory()) {
			  File files[] = fileOrPath.listFiles();
			  for(int i=0;i<files.length;i++) {
				  files[i].delete();
			  }
			}
			fileOrPath.delete();
		}
	}
	
	

	public static void main(String[] args) throws IOException {
		Thumbnails.of(new File("F:\\img\\xiaohuangren.jpg"))
		.size(500,500).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("F:\\image\\image\\watermark.jpg")), 0.25f)
		.outputQuality(0.8f).toFile("F:\\img\\xiaohuangrennew.jpg");
	}

}
